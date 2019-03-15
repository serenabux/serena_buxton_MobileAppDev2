//
//  BirthdayViewController.swift
//  Lab5
//
//  Created by Serena Buxton on 3/14/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class BirthdayViewController: UIViewController {

    @IBOutlet weak var birthdayLabel: UILabel!
    @IBOutlet weak var personLabel: UILabel!
    
    var bday : String?
    var bdayPerson : String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if bday != nil && bdayPerson != nil{
            birthdayLabel.text = bday!
            personLabel.text = bdayPerson! + "'s birthday is"
        }
        else{
            birthdayLabel.text = "Someone"
            personLabel.text = "Happy Birthday to"
        }

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
