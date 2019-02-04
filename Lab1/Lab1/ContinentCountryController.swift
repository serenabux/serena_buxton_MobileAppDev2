//
//  ContinentCountryController.swift
//  Lab1
//
//  Created by Serena Buxton on 1/30/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation
class ContientCountryController {
    var allData = [ContinentCountry]()
    let filename = "locations"
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: filename, withExtension: "plist"){
            //create plist decoder object
            let plistdecoder = PropertyListDecoder()
            do{
                let data = try Data(contentsOf: pathURL)
                //decodes the plist
                allData = try plistdecoder.decode([ContinentCountry].self, from: data)
            }
            catch{
                //handle error
                print(error)
            }
        }
    }
    
    func getContinents()->[String]{
        var continents = [String]()
        for c in allData{
            continents.append(c.continent)
        }
        return continents
    }
    
    func getCountires(index: Int)->[String]{
        return allData[index].country
    }
}
