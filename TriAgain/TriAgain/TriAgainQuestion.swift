//
//  TriAgainQuestion.swift
//  TriAgain
//
//  Created by Serena Buxton on 3/6/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import Foundation

struct TriAgainQuestionModel: Decodable {
    let solution : String
    let question : [String]
}

class TriAgainDataModelController {
    var allData = [[TriAgainQuestionModel]]()
    var fileName = "triAgainData"
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            let plistdecoder = PropertyListDecoder()
            do{
                let data = try Data(contentsOf: pathURL)
                allData = try plistdecoder.decode([[TriAgainQuestionModel]].self, from: data)
            }
            catch{
                print(error)
            }
        }
    }
    
    func getSolution(category:Int) -> [String]{
        var solutions = [String]()
        for item in allData[category]{
            solutions.append(item.solution)
        }
        return solutions
    }

    func getQuestion(category:Int, index:Int) -> [String]{
        var question = [String]()
        for q in allData[category][index].question{
            question.append(q)
        }
        return question
    }
}
