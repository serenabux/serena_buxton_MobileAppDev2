//
//  QuestionViewController.swift
//  TriAgain
//
//  Created by Serena Buxton on 3/4/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class QuestionViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var guessTextField: UITextField!
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var categoryLabel: UILabel!
    @IBOutlet weak var submitButton: UIButton!
    @IBOutlet weak var giveUpButton: UIButton!
    @IBOutlet weak var triAgainLabel: UILabel!
    @IBOutlet weak var pointsLabel: UILabel!
    
    var userGame2 = Game()
    var gameData = TriAgainDataModelController()
    var solutions = [String]()
    var questions = [String]()
    var question : String?
    var solution : String?
    var points = 3
    var totPoints = 0
    var done = false //false when start, change when user either gives up or gets correct answer to disable checking

    
    override func viewDidLoad() {
        super.viewDidLoad()
        super.becomeFirstResponder()
        
        guessTextField.delegate = self
        submitButton.isEnabled = false
        gameData.loadData()
        if userGame2.category != nil{
            let category = userGame2.category!
            categoryLabel.text = getCategory(randomNum: category)
            solutions = gameData.getSolution(category: category)
            let index = Int.random(in: 0 ..< solutions.count)
            solution = solutions[index]
            questions = gameData.getQuestion(category: category, index: index)
           question = questions[0] + " • " + questions[1] + " • " +  questions[2]
            questionLabel.text = question!
        }
        if userGame2.points != nil{
            totPoints = userGame2.points!
        }
        guessTextField.isUserInteractionEnabled = true
        pointsLabel.text = "Total Points: " + String(totPoints)
        triAgainLabel.isHidden = true
        
        //listening for keyboard events - also from Paul Solt tutorial - like adding event listeners to webpage in JS
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChange(notification:)), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChange(notification:)), name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChange(notification:)), name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
    }
    
//    deinit { //need to make sure to do this to not mess things up - also from Paul Solt tutorial
//        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillShowNotification, object: nil)
//        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
//        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
//
//    }

    @objc func keyboardWillChange(notification: Notification){ //code to move screen when the keyboard pops up modified from video tutorial by Paul Solt at https://www.youtube.com/watch?v=iUQ1GfiVzS0 and https://www.youtube.com/watch?v=xVZubAMFuIU
        if UIDevice.current.orientation.isLandscape && UIDevice.current.userInterfaceIdiom == .phone { //device orientation and userInterfaceIdiom found on apple documentation under UIDevice fter searching orientation and findign userInterfaceIdiom as a result for finding device type in google search
            guard let keyboardRect = (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue else{
                return
            }
            if notification.name == UIResponder.keyboardWillChangeFrameNotification || notification.name == UIResponder.keyboardWillShowNotification{
                view.frame.origin.y = -(keyboardRect.height)/2
            }else{
                view.frame.origin.y = 0
            }
        }
    }
    
    
    
//    @objc func adjustForKeyboard(notification: Notification) {
//        let userInfo = notification.userInfo!
//
//        let keyboardScreenEndFrame = (userInfo[UIResponder.keyboardFrameEndUserInfoKey] as! NSValue).cgRectValue
//        let keyboardViewEndFrame = view.convert(keyboardScreenEndFrame, from: view.window)
//
//        if notification.name == UIResponder.keyboardWillHideNotification {
//            guessTextField.contentInset = UIEdgeInsets.zero
//        } else {
//            guessTextField.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: keyboardViewEndFrame.height, right: 0)
//        }
//
//        guessTextField.scrollIndicatorInsets = guessTextField.contentInset
//
//        let selectedRange = guessTextField.selectedRange
//        guessTextField.scrollRangeToVisible(selectedRange)
//    }
    
    override var canBecomeFirstResponder: Bool {
        get {
            return true
        }
    }
    
    

    
    //get the label for the category label
    func getCategory(randomNum : Int)->String{
        switch randomNum {
        case 0:
            return "Category: Education"
        case 1:
            return "Category: Entertainment"
        case 2:
            return "Category: Sports/Recreation"
        default:
            return "Category: Miscellaneous"
        }
    }
    
    //check the users guess against the read in solution
    @IBAction func checkGuess(_ sender: Any) {
        if done == true{
            return
        }
        if (guessTextField != nil){
            var userInput = guessTextField.text!.lowercased()
            userInput = userInput.trimmingCharacters(in: .whitespacesAndNewlines) // reference: https://www.hackingwithswift.com/example-code/strings/how-to-trim-whitespace-in-a-string
            if (userInput == solution){
                triAgainLabel.text = "Great Job!"
                triAgainLabel.isHidden = false
                totPoints += points
                pointsLabel.text = "+" + String(points) + ", Total Points: "+String(totPoints)
                pointsLabel.isHidden = false
                done = true
                submitButton.isEnabled = false
                giveUpButton.isEnabled = false
            }
            else{
                if points == 1 {
                    done = true
                    triAgainLabel.text = "Solution: " + solution!
                    pointsLabel.text = "+0, Total Points: "+String(totPoints)
                    submitButton.isEnabled = false
                    guessTextField.isUserInteractionEnabled = false
                    
                }
                else{
                    triAgainLabel.text = "Try Again!"
                    points -= 1
                    if(points == 2){
                        pointsLabel.text =  "2 tries left"
                    }
                    else{
                        pointsLabel.text =  "1 try left"
                    }
                }
               triAgainLabel.isHidden = false
               pointsLabel.isHidden = false
            }
        }
    }
    
    override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
        if (motion == .motionShake) && done {
            guessTextField.text = ""
            let category = Int.random(in: 0 ..< 4)
            categoryLabel.text = getCategory(randomNum: category)
            solutions = gameData.getSolution(category: category)
            let index = Int.random(in: 0 ..< solutions.count)
            solution = solutions[index]
            questions = gameData.getQuestion(category: category, index: index)
            question = questions[0] + " • " + questions[1] + " • " +  questions[2]
            questionLabel.text = question!
            triAgainLabel.isHidden = true
            pointsLabel.text = "Total Points: " + String(totPoints)
            points = 3
            done = false
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        if (guessTextField != nil && !done){
            submitButton.isEnabled = true
        }
        else{
            submitButton.isEnabled = false
        }
        return true
    }
    
    
    @IBAction func touchDismissKeyboard(_ sender: UITapGestureRecognizer) {
        guessTextField.resignFirstResponder()
        if (guessTextField != nil && !done){
            submitButton.isEnabled = true
        }
        else{
            submitButton.isEnabled = false
        }
    }
    
    
    @IBAction func giveUp(_ sender: Any) {
        done = true
        triAgainLabel.text = "Solution: " + solution!
        triAgainLabel.isHidden = false
        pointsLabel.text = "+0, Total Points: " + String(points)
        pointsLabel.isHidden = false
        points = 0
        submitButton.isEnabled = false
        guessTextField.isUserInteractionEnabled = false
        giveUpButton.isEnabled = false
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "questionToCatSegue"{
            let questionViewController = segue.destination as! ViewController
            questionViewController.userGame.points = totPoints
        }
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