//
//  BirthdayDataController.swift
//  Lab5
//
//  Created by Serena Buxton on 3/13/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation
import Firebase

class BirthdayDataController {
    var ref: DatabaseReference!

    var birthdayData = [Birthday]()
    var onDataUpdate: ((_ data: [Birthday]) -> Void)?

    func setupFirebaseListener(){
        ref = Database.database().reference().child("Birthday")
        //set up a listener for Firebase data change events
        //this event will fire with the initial data and then all data changes
        ref.observe(DataEventType.value, with: {snapshot in
            self.birthdayData.removeAll()
            //DataSnapshot represents the Firebase data at a given time
            //loop through all the child data nodes
            for snap in snapshot.children.allObjects as! [DataSnapshot]{
                //print(snap)
                let birthdayID = snap.key
                if let birthdayDict = snap.value as? [String: String], //get value as a Dictionary
                    let birthdayName = birthdayDict["name"],
                    let birthdayDate = birthdayDict["date"]
                {
                    let newBirthday = Birthday(id: birthdayID, name: birthdayName, date: birthdayDate)
                    //add recipe to recipes array
                    self.birthdayData.append(newBirthday)
                    //print(recipeValue)
                }
                
            }
            //passing the results to the onDataUpdate closure
            self.onDataUpdate?(self.birthdayData)
        })
    }
    
    func getBirthdays()->[Birthday]{
        return birthdayData
    }
    
    func addBirthday(name:String, date:String){
        //create Dictionary
        let newBdayDict = ["name": name, "date": date]
        //create a new ID
        let bdayref = ref.childByAutoId()
        //write data to the new ID in Firebase
        bdayref.setValue(newBdayDict)
    }
    
    func deleteBirthday(birthdayID: String){
        // Delete the object from Firebase
        ref.child(birthdayID).removeValue()
    }
    
    
}
