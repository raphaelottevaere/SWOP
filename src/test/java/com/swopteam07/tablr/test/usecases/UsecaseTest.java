package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.UI.Component;
import com.swopteam07.tablr.controller.Controller;
import com.swopteam07.tablr.model.Database;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsecaseTest {

    Controller controller;
    Database database;

    @BeforeEach
    public void discardSingletons() {
        Controller.removeInstanceReference();
        Database.removeInstanceReference();

        controller = Controller.getInstance();
        database = Database.getInstance();
    }

    public void selectingLocked(Component expected, int yMin, int yMax, int xMin, int xMax) {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(xMax - xMin) + xMin;
            int y = random.nextInt(yMax - yMin) + yMin;
            if (!expected.contains(x, y)) controller.getActiveView().handleMouseEvent(500, x, y, 1);
            assertEquals(expected, controller.getActiveView().getRoot().getSelectedComponent());
        }
    }
}
