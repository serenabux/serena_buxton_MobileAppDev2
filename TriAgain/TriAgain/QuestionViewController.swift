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
    var done = false //false when start, change when user either gives up or gets correct answer to disable checking

    
    override func viewDidLoad() {
        super.viewDidLoad()
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
        guessTextField.isUserInteractionEnabled = true
        pointsLabel.isHidden = true
        triAgainLabel.isHidden = true
        
        
        
    }
    
    
    //get the label for the category label
    func getCategory(randomNum : Int)->String{
        switch randomNum {
        case 0:
            return "Category: Education"
        case 1:
            return "Category: Entertainment"
        case 2:
            return "Category: Sports/ Recreation"
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
            if (guessTextField.text!.lowercased() == solution){
                triAgainLabel.text = "Great Job!"
                triAgainLabel.isHidden = false
                pointsLabel.text = "+" + String(points)
                pointsLabel.isHidden = false
                done = true
                submitButton.isEnabled = false
                giveUpButton.isEnabled = false
            }
            else{
                if points == 1 {
                    done = true
                    triAgainLabel.text = "Solution: " + solution!
                    pointsLabel.text = "+0"
                    submitButton.isEnabled = false
                    guessTextField.isUserInteractionEnabled = false
                    
                }
                else{
                    triAgainLabel.text = "Tri Again!"
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
    
    @IBAction func giveUp(_ sender: Any) {
        done = true
        triAgainLabel.text = "Solution: " + solution!
        triAgainLabel.isHidden = false
        pointsLabel.text = "+0"
        pointsLabel.isHidden = true
        points = 0
        submitButton.isEnabled = false
        guessTextField.isUserInteractionEnabled = false
        giveUpButton.isEnabled = false
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        print(segue.identifier)
        if segue.identifier == "questionSegue"{
            let questionViewController = segue.destination as! ViewController
            questionViewController.userGame.points! += points
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
