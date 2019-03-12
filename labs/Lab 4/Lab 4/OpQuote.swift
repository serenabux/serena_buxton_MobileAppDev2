//
//  OpQuote.swift
//  Lab 4
//
//  Created by Serena Buxton on 3/2/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct OpQuote: Decodable{
    let quote: String
    let author: String
}

struct OpQuoteData: Decodable {
    var quotes = [OpQuote]()
}
