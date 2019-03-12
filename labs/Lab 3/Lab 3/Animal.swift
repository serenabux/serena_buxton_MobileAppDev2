//
//  Animal.swift
//  Lab 3
//
//  Created by Serena Buxton on 2/27/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct Animal: Decodable {
    let name : String
    let url : String
}

class AnimalDataModelController{
    var allData = [Animal]()
    let fileName = "AusAnimals"
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            let plistdecoder = PropertyListDecoder()
            do{
                let data = try Data(contentsOf: pathURL)
                allData = try plistdecoder.decode([Animal].self, from: data)
            }
            catch{
                print(error)
            }
        }
    }
    
    func getAnimals() -> [String]{
        var animals = [String]()
        for animal in allData{
            animals.append(animal.name)
        }
        return animals
    }
    
    func getURL(index:Int) -> String {
        return allData[index].url
    }
}
