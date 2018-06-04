package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Inventory;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static String url = "jdbc:mysql://localhost:3306/sdvid";

	@Override
	public Film getFilmById(int filmId) throws SQLException {
		if (filmId <= 0 || filmId > 1000) {
			return null;
		}

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(url, user, pass);
		String sqltext;
		sqltext = "SELECT film.id, title, description, release_year, language.name, rental_duration, rental_rate, length, replacement_cost, rating, special_features, category.name FROM film JOIN language ON language.id = language_id JOIN film_category ON film.id = film_id JOIN category ON category.id = category_id WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sqltext);
		stmt.setInt(1, filmId);
		List<String> categories = new ArrayList<>();

		ResultSet rs = stmt.executeQuery();
		Film film = new Film();
		if (rs.next()) {
			int filmId2 = rs.getInt(1);
			String title = rs.getString(2);
			String desc = rs.getString(3);
			short releaseYear = rs.getShort(4);
			String lang = rs.getString(5);
			int rentDur = rs.getInt(6);
			double rate = rs.getDouble(7);
			int length = rs.getInt(8);
			double repCost = rs.getDouble(9);
			String rating = rs.getString(10);
			String features = rs.getString(11);
			categories.add(rs.getString(12));
			film = new Film(filmId2, title, desc, releaseYear, lang, rentDur, rate, length, repCost, rating, features,
					getActorsByFilmId(filmId));
			film.setCategories(categories);
		}
		return film;
	}

	@Override
	public Actor getActorById(int actorId) throws SQLException {

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(url, user, pass);
		String sqltext;
		sqltext = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sqltext);
		stmt.setInt(1, actorId);

		ResultSet rs = stmt.executeQuery();
		Actor actor = new Actor();
		if (rs.next()) {
			int id = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);

			actor = new Actor(id, firstName, lastName);
		}
		rs.close();
		stmt.close();
		conn.close();
		return actor;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) throws SQLException {
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(url, user, pass);
		String sqltext;
		sqltext = "SELECT actor_id FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE film_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sqltext);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		List<Actor> list = new ArrayList<Actor>();
		while (rs.next()) {
			list.add(getActorById(rs.getInt(1)));
		}
		rs.close();
		stmt.close();
		conn.close();
		return list;
	}

	@Override
	public List<Film> getFilmBySearch(String word) throws SQLException {
		if (word == null || word.equals("")) {
			return null;
		}

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(url, user, pass);
		String sqltext;
		sqltext = "SELECT film.id, title, description, release_year, name, rental_duration, rental_rate, length, replacement_cost, rating, special_features FROM film JOIN language ON language.id = language_id WHERE description LIKE ? OR title LIKE ? ORDER BY film.id ASC";
		PreparedStatement stmt = conn.prepareStatement(sqltext);
		stmt.setString(1, "%" + word + "%");
		stmt.setString(2, "%" + word + "%");

		ResultSet rs = stmt.executeQuery();
		Film film = new Film();
		List<Film> filmList = new ArrayList<>();
		while (rs.next()) {
			int filmId2 = rs.getInt(1);
			String title = rs.getString(2);
			String desc = rs.getString(3);
			short releaseYear = rs.getShort(4);
			String lang = rs.getString(5);
			int rentDur = rs.getInt(6);
			double rate = rs.getDouble(7);
			int length = rs.getInt(8);
			double repCost = rs.getDouble(9);
			String rating = rs.getString(10);
			String features = rs.getString(11);

			film = new Film(filmId2, title, desc, releaseYear, lang, rentDur, rate, length, repCost, rating, features,
					getActorsByFilmId(filmId2));
			filmList.add(film);
		}
		rs.close();
		stmt.close();
		conn.close();
		return filmList;
	}

	public List<Inventory> getInventoryAndCondition(int filmId) throws SQLException {
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(url, user, pass);
		String sqltext;
		sqltext = "SELECT film.title, media_condition, store_id FROM film JOIN inventory_item on film.id = film_id WHERE film.id = ? order by store_id asc";
		PreparedStatement stmt = conn.prepareStatement(sqltext);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		List<Inventory> list = new ArrayList<Inventory>();
		while (rs.next()) {
			String title = rs.getString(1);
			String condition = rs.getString(2);
			int storeId = rs.getInt(3);
			Inventory movie = new Inventory(condition, storeId, title);
			list.add(movie);
			
		}
		rs.close();
		stmt.close();
		conn.close();
		return list;
	}
}
