package com.skilldistillery.filmquery.database;

import java.sql.SQLException;
import java.util.List;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Inventory;

public interface DatabaseAccessor {
  public Film getFilmById(int filmId) throws SQLException;
  public Actor getActorById(int actorId) throws SQLException;
  public List<Actor> getActorsByFilmId(int filmId) throws SQLException;
  public List<Film> getFilmBySearch(String word) throws SQLException;
  public List<Inventory> getInventoryAndCondition(int filmId) throws SQLException;
}
