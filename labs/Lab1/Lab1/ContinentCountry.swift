//
//  ContinentCountry.swift
//  Lab1
//
//  Created by Serena Buxton on 1/30/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct ContinentCountry: Decodable {
    let continent: String
    let country: [String]
}
