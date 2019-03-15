//
//  AddViewController.swift
//  Lab5
//
//  Created by Serena Buxton on 3/13/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class AddViewController: UIViewController {

    @IBOutlet weak var addName: UITextField!
    @IBOutlet weak var addBirthday: UITextField!
    
    var addedbirthday = String()
    var addedname = String()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "savesegue"{
            if addName.text?.isEmpty == false && addBirthday.text?.isEmpty == false{
                addedbirthday = addBirthday.text!
                addedname = addName.text!
            }
        }
    }

}
