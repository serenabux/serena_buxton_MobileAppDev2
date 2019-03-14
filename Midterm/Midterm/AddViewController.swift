//
//  AddViewController.swift
//  Midterm
//
//  Created by Serena Buxton on 3/14/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class AddViewController: UIViewController {
    
    var addedMovie = String()
    var addedURL = String()

    @IBOutlet weak var urlText: UITextField!
    @IBOutlet weak var movieText: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "saveSegue"{
            //only add a country if there is text in the textfield
            if movieText.text?.isEmpty == false{
                addedMovie=movieText.text!
            }
            if urlText.text?.isEmpty == false{
                addedURL=urlText.text!
            }
        }
    }

}
