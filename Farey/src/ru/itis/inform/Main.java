package ru.itis.inform;

public class Main {

    public static void main(String[] args) {
        FareySequenceGenerateArrayImpl F = new FareySequenceGenerateArrayImpl();
        F.Generate(10);
        F.Print();
    }
}