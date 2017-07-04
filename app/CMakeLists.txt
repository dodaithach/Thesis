cmake_minimum_required(VERSION 3.4.1)

add_subdirectory(src/main/cpp/openalsoft)

add_library( native-lib
             SHARED
             src/main/cpp/native-lib.cpp
             src/main/cpp/BinauralSound.h
             src/main/cpp/BinauralSound.cpp )

find_library( log-lib
              log )

target_link_libraries( native-lib
                       OpenAL
                       ${log-lib} )