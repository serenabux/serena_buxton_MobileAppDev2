//
//  ViewController.swift
//  Lab 4
//
//  Created by Serena Buxton on 2/28/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    //var currentAdvice : AdviceClass? = nil
    var getQuote = [OpQuote]()

    @IBOutlet weak var quoteLabel: UILabel!
    @IBOutlet weak var authorLabel: UILabel!
    
    @IBAction func newQuote(_ sender: UIButton) {
        loadjson()
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadjson()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    func loadjson(){
        let urlPath = "https://opinionated-quotes-api.gigalixirapp.com/v1/quotes"
        guard let url = URL(string: urlPath)
            else {
                print("url error")
                return
        }
        
        let session = URLSession.shared.dataTask(with: url, completionHandler: {(data, response, error) in
            let httpResponse = response as! HTTPURLResponse
            let statusCode = httpResponse.statusCode
            guard statusCode == 200
                else {
                    print("file download error")
                    return
            }
            //download successful
            print("download complete")
            //parse json asynchronously
            DispatchQueue.main.async {self.parsejson(data!)}
        })
        //must call resume to run session
        session.resume()
    }
    
    func parsejson(_ data: Data){
        do
        {
            let api = try JSONDecoder().decode(OpQuoteData.self, from: data)
            getQuote = api.quotes
            quoteLabel.text = getQuote[0].quote
            authorLabel.text = "-" + getQuote[0].author
        }
        catch let jsonErr
        {
            print(jsonErr.localizedDescription)
            return
        }
        //tableVIew.reloadData()
        
        
    }

}

