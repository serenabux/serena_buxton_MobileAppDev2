//
//  AddWorkoutViewController.swift
//  Lab 2
//
//  Created by Serena Buxton on 2/13/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class AddWorkoutViewController: UIViewController, UITextFieldDelegate{
    
    
    @IBOutlet weak var workoutText: UITextField!
    var addedWorkout = String()

    override func viewDidLoad() {
        super.viewDidLoad()
        workoutText.delegate = self
        // Do any additional setup after loading the view.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "donesegue"{
            //only do if there is text
            if(workoutText.text?.isEmpty) == false {
                addedWorkout = workoutText.text!
            }
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        workoutText.resignFirstResponder()
        return true
    }
    
    /*func textFieldDidEndEditing(_ textField: UITextField) {
      
    }*/
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
