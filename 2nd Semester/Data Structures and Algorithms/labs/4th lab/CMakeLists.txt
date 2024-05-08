cmake_minimum_required(VERSION 3.27)
project(sda3nou)

set(CMAKE_CXX_STANDARD 23)

add_executable(sda3nou
        Colectie.cpp
        Colectie.h
        App.cpp
        IteratorColectie.cpp
        IteratorColectie.h
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
