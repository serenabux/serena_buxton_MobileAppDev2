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
    @IBOutlet weak var instructionLabel: UILabel!
    
    var userGame = Game()
    var category = -1
    var points = 0
    var setCategory = false
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        super.becomeFirstResponder()
        categoryImage.image = UIImage(named: "bluePlaceHolder")
        categoryLabel.isHidden = true
        userGame.points = 0
        instructionLabel.isHidden = false
        setCategory = false
    }
    
//    override func viewDidAppear(_ animated: Bool) {
//
//
//
//
//    }
    
    //in order to use shake
    override var canBecomeFirstResponder: Bool {
        get {
            return true
        }
    }
    
    //sets label and image for category
    func setCategoryInfo(cat: Int){
        switch(cat){
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
    }
    
    // Enable detection of shake motion
    //reference https://stackoverflow.com/questions/33503531/detect-shake-gesture-ios-swift
    override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
        if motion == .motionShake {
            //random reference: https://learnappmaking.com/random-numbers-swift/
            if setCategory == false{
                category = Int.random(in: 0 ..< 4)
                setCategoryInfo(cat: category)
                instructionLabel.isHidden = true
                categoryImage.isHidden = false
                categoryLabel.isHidden = false
                playButton.isEnabled = true
                setCategory = true
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "questionSegue"{
            let questionViewController = segue.destination as! QuestionViewController
            questionViewController.userGame2.category = category
            questionViewController.userGame2.points = points
            categoryImage.image = UIImage(named: "bluePlaceHolder")
            categoryLabel.isHidden = true
        }
        
        if segue.identifier == "helpSegue"{
            userGame.category = category
            userGame.points = points
        }
    }
    
    @IBAction func unwindSegue (_ segue:UIStoryboardSegue){
        if segue.identifier == "questionToCatSegue"{
            //update the score based on points
            if(userGame.points != nil){
                points += (userGame.points)!
                pointsLabel.text = "Points: " + String(points)
            }
            if(userGame.catSet != nil){
                if(userGame.catSet == true){
                    category = Int.random(in: 0 ..< 4)
                    userGame.category = category
                    setCategoryInfo(cat: category)
                    instructionLabel.isHidden = true
                    categoryImage.isHidden = false
                    categoryLabel.isHidden = false
                    playButton.isEnabled = true
                    setCategory = true
                }
                else{
                    if(userGame.category != nil){
                        if(userGame.category! != -1){
                            setCategoryInfo(cat: userGame.category!)
                            setCategory = true
                        }
                        else{
                            categoryImage.image = UIImage(named: "bluePlaceHolder")
                            categoryLabel.isHidden = true
                            playButton.isEnabled = false
                            instructionLabel.isHidden = false
                            setCategory = false
                        }
                    }
                    else{
                        categoryImage.image = UIImage(named: "bluePlaceHolder")
                        categoryLabel.isHidden = true
                        playButton.isEnabled = false
                        instructionLabel.isHidden = false
                        setCategory = false
                    }
                }
                
            }
            
            
        }
        if segue.identifier == "returnFromHelp"{
            if (userGame.points != nil){
                points = userGame.points!
            }
            if(userGame.category != nil){
                category = userGame.category!
            }

        }
    }

    //reset the score and category
    @IBAction func newGame(_ sender: UIBarButtonItem) {
        categoryImage.image = UIImage(named: "bluePlaceHolder")
        categoryLabel.isHidden = true
        userGame.points = 0
        instructionLabel.isHidden = false
        pointsLabel.text = "Points: 0"
        userGame.category = -1
        
    }
    
}

