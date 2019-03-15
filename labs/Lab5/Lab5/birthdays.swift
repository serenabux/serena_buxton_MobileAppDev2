//
//  birthdays.swift
//  Lab5
//
//  Created by Serena Buxton on 3/13/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct Birthday {
    var id: String
    var name: String
    var date: String
    init(id: String, name: String, date: String){
        self.id = id
        self.name = name
        self.date = date
    }
}
