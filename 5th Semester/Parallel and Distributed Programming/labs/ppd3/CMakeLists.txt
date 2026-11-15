cmake_minimum_required(VERSION 3.26)
project(ppd3)

set(CMAKE_CXX_STANDARD 20)

# ---------------------------
# MS-MPI Configuration
# ---------------------------
set(MSMPI_INC "C:/Program Files (x86)/Microsoft SDKs/MPI/Include")
set(MSMPI_LIB_DIR "C:/Program Files (x86)/Microsoft SDKs/MPI/Lib/x64")
set(MSMPI_LIB "msmpi.lib")


# Include headers
include_directories(${MSMPI_INC})

# Link library folder
link_directories(${MSMPI_LIB_DIR})

# ---------------------------
# Executable
# ---------------------------
add_executable(ppd3
        AsyncCalculation.h
        GenerateNumber.h
        ScatterCalculation.h
        SequentialCalculation.h
        StandardCalculation.h
        AsyncCalculation.cpp
        GenerateNumber.cpp
        ScatterCalculation.cpp
        SequentialCalculation.cpp
        StandardCalculation.cpp
        main.cpp
        StandardClassicCalculation.h
        StandardClassicCalculation.cpp
)

# ---------------------------
# Link MPI library
# ---------------------------
target_link_libraries(ppd3 ${MSMPI_LIB})
