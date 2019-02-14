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
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            let plistdecoder = PropertyListDecoder()
            do{
                let data = try Data(contentsOf: pathURL)
                allData = try plistdecoder.decode([workoutDataModel].self, from: data)
            }
            catch{
                print(error)
            }
        }
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
