package com.github.matcaban.pokemonmanager.service;

import com.github.matcaban.pokemonmanager.db.HikariCPDataSource;
import com.github.matcaban.pokemonmanager.domain.Pokemon;
import com.github.matcaban.pokemonmanager.domain.Trainer;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class DBTrainerService {
    private static String READ_ALL_TRAINERS = "SELECT * FROM trainer";

    private static Logger logger = getLogger(DBTrainerService.class);


    public List<Trainer> getAllTrainers() {
        List<Pokemon> pokemons = new DBPokemonService().getAllPokemon();
        List<Trainer> trainers = this.setTrainers();

        for (Trainer trainer: trainers) {
            for (Pokemon pokemon: pokemons) {
                if (Objects.equals(pokemon.getTrainerId(), trainer.getId())) {
                    trainer.addPokemon(pokemon);
                }
            }
        }
        return trainers;
    }

    private List<Trainer> setTrainers() {
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
}
