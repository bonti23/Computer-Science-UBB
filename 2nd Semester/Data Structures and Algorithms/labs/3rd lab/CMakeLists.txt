cmake_minimum_required(VERSION 3.27)
project(sda2)

set(CMAKE_CXX_STANDARD 23)

add_executable(sda2
        MDO.cpp
        MDO.h
        IteratorMDO.cpp
        IteratorMDO.h
        App.cpp
        TestScurt.cpp
        TestScurt.h
        TestExtins.cpp
        TestExtins.h)
