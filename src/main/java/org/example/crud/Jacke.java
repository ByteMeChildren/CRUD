package org.example.crud;

import jakarta.persistence.Id;

import lombok.Data;

@Data //macht Getter/Setter/Constructor für Dich
public class Jacke // generell schreiben wir englischen Code: Jacket
{
    @Id//braucht es glaube ich nicht, aber ich mache es sicherheits halber -> nein braucht's nicht: Wir haben keine DB.
    private int id;
    private String name;
    private String brand;
    private Size size;
    private String color;
    private Season season;
    private int releaseYear;
}

enum Size
{
    XS, S, M, L, XL
}

enum Season
{
    SPRING, SUMMER, AUTUMN, WINTER // (gemäss Sonar) enum Konstanten immer UPPERCASE
}
