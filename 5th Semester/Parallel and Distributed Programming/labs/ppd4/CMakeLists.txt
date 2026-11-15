cmake_minimum_required(VERSION 4.0)
project(ppd4)

set(CMAKE_CXX_STANDARD 20)

add_executable(ppd4 main.cpp
        CalculateNotes.cpp
        CalculateNotes.h
        CalculateNotesParallel.cpp
        CalculateNotesParallel.h
        GenerateData.cpp
        GenerateData.h
        LinkedList.cpp
        LinkedList.h
        QueueContainer.cpp
        QueueContainer.h)
