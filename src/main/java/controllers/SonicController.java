package controllers;

import models.Model;

import java.net.URL;
import java.util.*;

public class SonicController extends HeroesController {


    private Model modelSonic = new Model("sonicSave.txt");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setModel(modelSonic);
        loadFile();

    }


}



