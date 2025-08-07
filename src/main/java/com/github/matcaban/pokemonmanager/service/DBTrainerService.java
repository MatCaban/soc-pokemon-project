package com.github.matcaban.pokemonmanager.service;

import com.github.matcaban.pokemonmanager.db.HikariCPDataSource;
import com.github.matcaban.pokemonmanager.domain.Pokemon;
import com.github.matcaban.pokemonmanager.domain.Trainer;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class DBTrainerService {
    private static String READ_ALL_TRAINERS = "SELECT * FROM trainer";

    private static String CREATE_TRAINER = "INSERT INTO trainer (name) VALUES (?)";

    private static String DELETE_TRAINER = "DELETE FROM trainer WHERE id = ?";

    private static String UPDATE_TRAINER_NAME = "UPDATE trainer SET name = ? WHERE id = ?";

    private static Logger logger = getLogger(DBTrainerService.class);


    public List<Trainer> getAllTrainersWithPokemons() {
        List<Pokemon> pokemons = new DBPokemonService().getAllPokemon();
        List<Trainer> trainers = this.getAllTrainers();

        for (Trainer trainer: trainers) {
            for (Pokemon pokemon: pokemons) {
                if (Objects.equals(pokemon.getTrainerId(), trainer.getId())) {
                    trainer.addPokemon(pokemon);
                }
            }
        }
        return trainers;


    }

    private List<Trainer> getAllTrainers() {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_TRAINERS);
                ResultSet rs = statement.executeQuery();
                ) {
            List<Trainer> trainers = new ArrayList<>();
            while (rs.next()) {
                trainers.add(
                        new Trainer(
                                rs.getInt("id"),
                                rs.getString("name")
                        )
                );
            }
            return trainers;
        } catch (SQLException e) {
            logger.error("Error while reading trainers");
            return null;
        }
    }

    public int createTrainer(String name) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_TRAINER);
                ){
            statement.setString(1, name);
            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("I am sorry trainer with this name already exists");
            return 0;
        }catch (SQLException e) {
            logger.error("Error while creating trainer");
            return 0;
        }
    }

    public int deleteTrainer(int id) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_TRAINER);
                ) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting trainer");
            return 0;
        }
    }

    public int updateTrainer(String name, int id) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_TRAINER_NAME);
                ) {
            statement.setString(1, name);
            statement.setInt(2, id);
            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Trainer with this name already exists");
            return 0;
        }
        catch (SQLException e) {
            logger.error("Error while renamig trainer");
            return 0;
        }
    }
}
