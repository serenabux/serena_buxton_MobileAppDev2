//
//  Movie.swift
//  Midterm
//
//  Created by Serena Buxton on 3/14/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct MovieDataModel: Decodable{
    let name : String
    let url : String
}

class MovieDataModelController {
    var allData = [MovieDataModel]()
    let fileName = "movies"
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            //creates a property list decoder object
            let plistdecoder = PropertyListDecoder()
            do {
                let data = try Data(contentsOf: pathURL)
                //decodes the property list
                allData = try plistdecoder.decode([MovieDataModel].self, from: data)
            } catch {
                // handle error
                print(error)
            }
        }
    }
    
    func getMovies() -> [String]{
        var movies = [String]()
        for m in allData{
            movies.append(m.name)
        }
        return movies
    }
    
    func getURL(index:Int) -> String {
        return allData[index].url
    }
}
