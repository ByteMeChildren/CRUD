package org.example.crud;

import lombok.Data;
import jakarta.persistence.Id;

@Data//macht Getter/Setter/Konstructor für Dich
public class Jacke
{
    @Id//braucht es glaube ich nicht, aber ich mache es sicherheits halber
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
    Spring, Summer, Autumn, Winter
}