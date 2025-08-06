package com.github.matcaban.pokemonmanager.service;

import com.github.matcaban.pokemonmanager.db.HikariCPDataSource;
import com.github.matcaban.pokemonmanager.domain.Pokemon;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DBPokemonService {
    private static final String READ_ALL_POKEMONS = "SELECT p.id, nt.name, nt.type, p.unique_trait, p.trainer_id " +
            "FROM pokemon p " +
            "LEFT JOIN name_type nt ON nt.id = p.name_id";
    private static final String GET_ID_OF_NAME = "SELECT id FROM name_type WHERE name = ?";

    private static final String CREATE_POKEMON = "INSERT INTO pokemon(name_id,unique_trait)" +
            "VALUES (?,?)";

    private static final String DELETE_POKEMON = "DELETE FROM pokemon WHERE id = ?";

    private static final String UPDATE_UNIQUE_TRAIT = "UPDATE pokemon SET unique_trait = ? WHERE id = ?";


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


    public int create(int id, String unique_trait) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_POKEMON);
                ) {
            statement.setInt(1, id);
            statement.setString(2, unique_trait);
            return statement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("There already is pokemon in my pokedex with this trait.");
            return 0;
        } catch (SQLException e) {
            logger.error("Error while creating new pokemon");
            return 0;
        }
    }

    public int delete(int id) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_POKEMON);
                ) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting pokemon");
            return 0;
        }
    }

    public int updateUniqueTrait(String newValue, int id) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_UNIQUE_TRAIT);
                ) {
            statement.setString(1, newValue);
            statement.setInt(2, id);
            return statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Pokemon with this trait already exist");
            return 0;
        }
        catch (SQLException e) {
            logger.error("Error while updating unique value");
            return 0;
        }
    }

    // get the ID of existing pokemon, if pokemon with that name does not exists return -1
    public int getIdOfName(String name) {
        try (
                Connection connection = HikariCPDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ID_OF_NAME);
        ) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            logger.error("Error while searching for id", e);
            return -1;
        }
    }
}
