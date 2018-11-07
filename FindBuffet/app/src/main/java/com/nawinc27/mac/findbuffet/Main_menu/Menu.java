package com.nawinc27.mac.findbuffet.Main_menu;

public class Menu {
    private String name;
    private String nameEng;
    private String path_image;

    public Menu(String name, String nameEng, String path_image) {
        this.name = name;
        this.nameEng = nameEng;
        this.path_image = path_image;
    }

    public Menu(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }
}
