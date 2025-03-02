cmake_minimum_required(VERSION 3.29)
project(laborator_6_oop)

set(CMAKE_CXX_STANDARD 20)
include_directories(${CMAKE_SOURCE_DIR}/domain)
include_directories(${CMAKE_SOURCE_DIR}/repository)
include_directories(${CMAKE_SOURCE_DIR}/service)
include_directories(${CMAKE_SOURCE_DIR}/ui)


add_executable(laborator_6_oop main.cpp
                domain/Contract.cpp
                domain/Validator.cpp
                repository/Repository.cpp
                service/Service.cpp
                ui/Console.cpp


)