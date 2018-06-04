package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Inventory;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();
	}

	@SuppressWarnings("unused")
	private void test() throws SQLException {
		Film film = db.getFilmById(1);
		System.out.println(film);
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);
		userMenu(input);
		// startUserInterface(input);

		input.close();
	}

	private void userMenu(Scanner input) throws SQLException {
		int choice = 0;
		while (choice != 3) {
			System.out.println(
					"What would you like to do?\n1. Look up a film by ID\n2. Look up a film by keyword\n3. Exit");
			choice = input.nextInt();
			input.nextLine();
			if (choice == 1) {
				System.out.println("Please enter the film ID");
				int filmID = input.nextInt();
				input.nextLine();
				Film film = db.getFilmById(filmID);
				if (film == null) {
					System.out.println("I am sorry, but the film ID you specified is not in our directory.");
				} else {
					System.out.println(film);
					subMenu(input, film);
				}
			} else if (choice == 2) {
				String keyword;
				System.out.println("Please enter a keyword, we will search the title and description of our films.");
				keyword = input.nextLine();

				while (keyword.isEmpty()) {
					System.out.println("Please enter something to search for.");
					keyword = input.nextLine();
				}
				List<Film> filmlist = db.getFilmBySearch(keyword);
				if (filmlist.isEmpty()) {
					System.out.println("I am sorry, but the film you specified is not in our directory.");
				} else {
					for (Film film : filmlist) {
						System.out.println(film);
						System.out.println("Cast: " + film.getActorList() + "\n");

					}
				}
			} else {
				System.out.println("Goodbye!");
				System.exit(0);
			}
		}
	}

	private void subMenu(Scanner input, Film film) throws SQLException {
		int choice = 0;
		while (choice != 2) {
			System.out.println("Would you like to view the movies full details? \n1. Yes 2. Return to Main Menu");
			choice = input.nextInt();
			if (choice == 1) {
				System.out.println(film);
				System.out.println("Categories: " + film.getCategories());
				System.out.println("Cast: " + film.getActorList());
				List<Inventory> list = db.getInventoryAndCondition(film.getId());
				System.out.println("Are you interested in finding a copy? \n1. Yes \n2. No");
				choice = input.nextInt();
				input.nextLine();
				if (choice == 1) {
					System.out.println(
							"Would you like to view all copies or a specific store?\n1. Store \n2. All Copies");
					choice = input.nextInt();
					input.nextLine();
					if (choice == 1) {
						System.out.println("Please enter a store number (1-8).");
						choice = input.nextInt();
						input.nextLine();
						System.out.println("Copies of " + film.getTitle());
						for (Inventory inventory : list) {
							if (inventory.getStoreID() == choice) {
								System.out.println(inventory);
							}
						}
					} else {

						for (Inventory inventory : list) {
							System.out.println(inventory);

						}
					}

					choice = 2;
				} else
					choice = 2;
			}
		}
	}

	@SuppressWarnings("unused")
	private void startUserInterface(Scanner input) throws SQLException {
		System.out.println("What film bish?");
		int choice = input.nextInt();
		Film film = db.getFilmById(choice);
		System.out.println(film);
		System.out.println("Would you like to see the actors? \n[Y] \n[N]");
		input.nextLine();
		String userInput = input.nextLine();
		if (userInput.equals("Y")) {
			System.out.println(film.getActorList());
		} else {
			System.out.println("ok bye");
		}

	}

}
