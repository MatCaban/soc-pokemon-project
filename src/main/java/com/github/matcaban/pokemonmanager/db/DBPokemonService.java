package com.github.matcaban.pokemonmanager.db;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DBPokemonService {
    private static final String READ_ALL_POKEMONS = "SELECT nt.id, nt.name, nt.type, p.unique_trait, p.trainer_id " +
            "FROM pokemon p " +
            "LEFT JOIN name_type nt ON nt.id = p.name_id";

    private static Logger logger = getLogger(DBPokemonService.class);

    public List<Pokemon> getAllPokemon() {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_POKEMONS);
                ResultSet rs = statement.executeQuery();
                ) {
            List<Pokemon> allPokemons = new ArrayList<>();

            while (rs.next()) {
                allPokemons.add(
                        new Pokemon(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("type"),
                                rs.getString("unique_trait"),
                                rs.getInt("trainer_id")
                        )
                );
            }
            return allPokemons;
        } catch (SQLException e) {
            logger.error("Error while reading pokemons", e);
            return null;
        }
    }
}
