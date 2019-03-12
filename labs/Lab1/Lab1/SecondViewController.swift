//
//  SecondViewController.swift
//  Lab1
//
//  Created by Serena Buxton on 1/27/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController {


    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    @IBAction func goToMaps(_ sender: UIButton) {
        if(UIApplication.shared.canOpenURL(URL(string: "comgooglemaps://")!)){
            UIApplication.shared.open(URL(string: "comgooglemaps://)")!, options: [:], completionHandler: nil)
        }
        else{
            if(UIApplication.shared.canOpenURL(URL(string: "maps://")!)){
                UIApplication.shared.open(URL(string:"maps://")!, options: [:], completionHandler: nil)
            }
            else {
                UIApplication.shared.open(URL(string: "http://www.google.com/maps/")!, options: [:], completionHandler: nil)
            }
        }
    }
    
}

