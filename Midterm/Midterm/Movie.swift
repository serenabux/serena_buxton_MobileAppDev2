//
//  Movie.swift
//  Midterm
//
//  Created by Serena Buxton on 3/14/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct MovieDataModel: Codable{
    var name : String
    let url : String
}

class MovieDataModelController {
    var allData = [MovieDataModel]()
    let fileName = "movies"
    let datafilename = "data.plist"
    
    func getDataFile(datafile: String) -> URL {
        //get path for data file
        let dirPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let docDir = dirPath[0] //documents directory
        print(docDir)
        
        // URL for our plist
        return docDir.appendingPathComponent(datafile)
    }
    
    func loadData(){

        
        let pathURL:URL?
        
        // URL for our plist
        let dataFileURL = getDataFile(datafile: datafilename)
        print(dataFileURL)
        
        //if the data file exists, use it
        if FileManager.default.fileExists(atPath: dataFileURL.path){
            pathURL = dataFileURL
        }
        else {
            // URL for our plist
            pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist")
        }
        
        //creates a property list decoder object
        let plistdecoder = PropertyListDecoder()
        do {
            let data = try Data(contentsOf: pathURL!)
            //decodes the property list
            allData = try plistdecoder.decode([MovieDataModel].self, from: data)
        } catch {
            // handle error
            print(error)
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
    
    func deleteMovie(index:Int){
        allData.remove(at: index)
   }
    
    func addMovie( newName: String, newURL: String){
        //create new movie instance
        let m = MovieDataModel(name: newName, url: newURL)
        allData.append(m)
    }
    
    func writeData(){
        // URL for our plist
        let dataFileURL = getDataFile(datafile: datafilename)
        print(dataFileURL)
        //creates a property list decoder object
        let plistencoder = PropertyListEncoder()
        plistencoder.outputFormat = .xml
        do {
            let data = try plistencoder.encode(allData.self)
            try data.write(to: dataFileURL)
        } catch {
            // handle error
            print(error)
        }
    }
}
