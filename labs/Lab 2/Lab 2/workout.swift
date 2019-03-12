//
//  workout.swift
//  Lab 2
//
//  Created by Serena Buxton on 2/11/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct workoutDataModel : Codable {
    var type : String
    var workouts : [String]
}

class workoutDataModelController {
    var allData = [workoutDataModel]()
    let fileName = "workout"
    let dataFile = "data.plist"
    
    func getDataFile(datafile: String) -> URL {
        //get path for data file
        let dirPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let docDir = dirPath[0]
        print(docDir)
        //url for our plist
        return docDir.appendingPathComponent(datafile)
    }
    func writeData(){
        //url for our plist
        let dataFileURL = getDataFile(datafile: dataFile)
        print(dataFileURL)
        //creates a plist decode object
        let plistencoder = PropertyListEncoder()
        plistencoder.outputFormat = .xml
        do{
            let data = try plistencoder.encode(allData.self)
            try data.write(to: dataFileURL)
        } catch{
            print(error)
        }
        
    }
    
    func loadData(){
        let pathURL:URL?
        
        //URL for our plist
        let dataFileURL = getDataFile(datafile: dataFile)
        print(dataFileURL)
        
        //if data file exists, use it
        if FileManager.default.fileExists(atPath: dataFileURL.path){
            pathURL = dataFileURL
        }
        else{
            //URL for plist
            pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist")
        }
        let plistdecoder = PropertyListDecoder()
        do{
            let data = try Data(contentsOf: pathURL!)
            allData = try plistdecoder.decode([workoutDataModel].self, from: data)
        }catch{
            print(error)
        }
        /*if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            let plistdecoder = PropertyListDecoder()
            do{
                let data = try Data(contentsOf: pathURL)
                allData = try plistdecoder.decode([workoutDataModel].self, from: data)
            }
            catch{
                print(error)
            }
        }*/
    }
    
    func getType() -> [String]{
        var types = [String]()
        for item in allData{
            types.append(item.type)
        }
        return types
    }
    
    func getWorkouts(index:Int) -> [String]{
        return allData[index].workouts
    }
    
    func addWorkout(index: Int, newWorkout: String, newIndex: Int){
        allData[index].workouts.insert(newWorkout, at: newIndex)
    }
    
    func deleteWorkout(index:Int, workoutIndex: Int){
        allData[index].workouts.remove(at: workoutIndex)
    }
}
