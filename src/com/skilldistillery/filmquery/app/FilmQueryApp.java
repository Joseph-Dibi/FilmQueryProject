package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		//app.test();
		app.launch();
	}

	private void test() throws SQLException {
		Film film = db.getFilmById(1);
		System.out.println(film);
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		System.out.println("What film bish?");
		int choice = input.nextInt();
		Film film = db.getFilmById(choice);
		System.out.println(film);
		System.out.println("Would you like to see the actors? \n[Y] \n[N]");
		input.nextLine();
		String userInput = input.nextLine();
		if(userInput.equals("Y")) {
			System.out.println(film.getActorList());			
		}
		else {
			System.out.println("ok bye");
		}

	}

}
