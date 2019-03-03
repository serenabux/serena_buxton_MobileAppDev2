//
//  Joke.swift
//  Lab 4
//
//  Created by Serena Buxton on 2/28/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct Joke: Decodable{
    let joke: String
}

struct JokeData: Decodable {
    var results = [Joke]()
}

