cmake_minimum_required(VERSION 4.0)
project(ppd2)

set(CMAKE_CXX_STANDARD 20)

add_executable(ppd2 main.cpp
        DynamicMatrices.cpp
        DynamicMatrices.h)
