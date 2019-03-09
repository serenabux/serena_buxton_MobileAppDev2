//
//  ViewController.swift
//  TriAgain
//
//  Created by Serena Buxton on 3/3/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var categoryImage: UIImageView!
    @IBOutlet weak var categoryLabel: UILabel!
    @IBOutlet weak var playButton: UIButton!
    @IBOutlet weak var pointsLabel: UILabel!
    
    var userGame = Game()
    var category = 0
    var points = 0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        super.becomeFirstResponder()
        categoryImage.image = UIImage(named: "bluePlaceHolder")
        categoryLabel.isHidden = true
    }
    
    override func viewDidAppear(_ animated: Bool) {
        categoryImage.image = UIImage(named: "bluePlaceHolder")
        categoryLabel.isHidden = true
        playButton.isEnabled = false
    }
    
    override var canBecomeFirstResponder: Bool {
        get {
            return true
        }
    }
    
    // Enable detection of shake motion
    //reference https://stackoverflow.com/questions/33503531/detect-shake-gesture-ios-swift
    override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
        if motion == .motionShake {
            //random reference: https://learnappmaking.com/random-numbers-swift/
            category = Int.random(in: 0 ..< 4)
            switch(category){
            case (0):
                categoryLabel.text = "Education"
                categoryImage.image = UIImage(named: "education")
            case(1):
                categoryLabel.text = "Entertainment"
                categoryImage.image = UIImage(named: "entertainment")
            case(2):
                categoryLabel.text = "Sports/Recreation"
                categoryImage.image = UIImage(named: "sports")
            default:
                categoryLabel.text = "Miscellaneous"
                categoryImage.image = UIImage(named: "misc")
            }
            categoryImage.isHidden = false
            categoryLabel.isHidden = false
            playButton.isEnabled = true
            
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "questionSegue"{
            let questionViewController = segue.destination as! QuestionViewController
            questionViewController.userGame2.category = category
            questionViewController.userGame2.points = points
            categoryImage.image = UIImage(named: "bluePlaceHolder")
            
        }
    }
    
    @IBAction func unwindSegue (_ segue:UIStoryboardSegue){
        if(userGame.points != nil){
            points += (userGame.points)!
            pointsLabel.text = "Points: " + String(points)
            
        }
        
        
        
    }


}

