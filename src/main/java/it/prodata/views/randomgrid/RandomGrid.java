/*
 * RandomGrid  2025-02-18
 *
 * Copyright (c) Pro Data GmbH & ASA KG. All rights reserved.
 */

package it.prodata.views.randomgrid;

import com.github.javafaker.Faker;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Stream;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 * RandomGrid
 * @author Matthias Perktold
 * @since 2025-02-18
 */
@PageTitle("Random Grid")
@Route("random-grid")
@Menu(order = 0, icon = LineAwesomeIconUrl.TABLE_SOLID)
public class RandomGrid extends VerticalLayout {

	private final Faker faker = new Faker();

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);

		var grid = new Grid<Person>();
		grid.addColumn(Person::firstname).setHeader("Firstname");
		grid.addColumn(Person::lastname).setHeader("Lastname");
		grid.addColumn(
			LitRenderer.<Person>of(
				"<vaadin-checkbox class='cell-renderer' ?checked=${item.vip} disabled></vaadin-checkbox>"
			).withProperty("vip", Person::vip)
		).setHeader("VIP");
		grid.setItems(generatePeople(100));

		var refresh = new Button("Refresh", e -> grid.setItems(generatePeople(100)));

		add(refresh);
		addAndExpand(grid);
	}

	private List<Person> generatePeople(int count) {
		return Stream.generate(this::generatePerson).limit(count).toList();
	}

	private Person generatePerson() {
		return new Person(faker.name().firstName(), faker.name().lastName(), faker.random().nextBoolean());
	}

	record Person(String firstname, String lastname, boolean vip) {}
}
