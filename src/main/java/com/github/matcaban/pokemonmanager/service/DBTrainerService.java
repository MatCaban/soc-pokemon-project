package com.github.matcaban.pokemonmanager.service;

import com.github.matcaban.pokemonmanager.db.HikariCPDataSource;
import com.github.matcaban.pokemonmanager.domain.Pokemon;
import com.github.matcaban.pokemonmanager.domain.Trainer;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DBTrainerService {
    private static String CREATE_TRAINER = "INSERT INTO trainer (name) VALUES (?)";

    private static String DELETE_TRAINER = "DELETE FROM trainer WHERE id = ?";

    private static String UPDATE_TRAINER_NAME = "UPDATE trainer SET name = ? WHERE id = ?";

    private static String READ_ALL_TRAINERS_WITH_POKEMON = "select t.id, t.name, " +
            "p.id, p.unique_trait, p.trainer_id, " +
            "nt.name, nt.type " +
            "from trainer t " +
            "left join pokemon p ON p.trainer_id = t.id " +
            "left join name_type nt on nt.id = p.name_id";

    private static Logger logger = getLogger(DBTrainerService.class);


    public List<Trainer> getAllTrainersWithPokemons() {
        List<Trainer> trainers = new ArrayList<>();
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_TRAINERS_WITH_POKEMON);
                ResultSet rs = statement.executeQuery();
        ) {

            while (rs.next()) {
                Trainer trainer = new Trainer(
                        rs.getInt("t.id"),
                        rs.getString("t.name")
                );

                if (!trainers.contains(trainer)) {
                    trainers.add(trainer);
                }

                int trainerId = rs.getInt("p.trainer_id");

                for (Trainer t : trainers) {
                    if (trainerId == t.getId()) {
                        t.addPokemon(
                                new Pokemon(
                                        rs.getInt("p.id"),
                                        rs.getString("nt.name"),
                                        rs.getString("nt.type"),
                                        rs.getString("p.unique_trait"),
                                        rs.getInt("t.id")
                                )
                        );
                    }
                }

            }
        } catch (SQLException e) {
            logger.error("Error while reading trainers and pokemons");
        }

        return trainers;
    }


    public int createTrainer(String name) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_TRAINER);
        ) {
            statement.setString(1, name);
            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("I am sorry trainer with this name already exists");
            return 0;
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            logger.error("Error while renaming trainer");
            return 0;
        }
    }
}
