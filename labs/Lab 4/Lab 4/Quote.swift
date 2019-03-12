//
//  Quote.swift
//  Lab 4
//
//  Created by Serena Buxton on 2/28/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct Quote: Decodable{
    let quote: String
    let author: String
}

struct QuoteData: Decodable {
    var quotes = [Quote]()
}
