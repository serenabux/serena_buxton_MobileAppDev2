//
//  FirstViewController.swift
//  Lab1
//
//  Created by Serena Buxton on 1/27/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {

    @IBOutlet weak var locationPicker: UIPickerView!
    @IBOutlet weak var suggestionLabel: UILabel!
    
    let continentComponent = 0
    let countryComponenent = 1
    
    var contientCountries = ContientCountryController()
    var countries = [String]()
    var continents = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        contientCountries.loadData()
        continents = contientCountries.getContinents()
        countries = contientCountries.getCountires(index: 0)
        suggestionLabel.text = "You should visit New York City"
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if component == continentComponent {
            return continents.count
        }
        else{
            return countries.count
        }
    }
    
    //Picker Delegate methods
    //Returns title of row
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        //checks which componenent was picked and returns the value for the requested component
        if component == continentComponent{
            return continents[row]
        }
        else{
            return countries[row]
        }
    }
    
    //Called when row is selected
    //only one that is different is dependant picker
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        //checks which componenent was picked
        // if it is the first one, want to get the updated picker for next component
        if component == continentComponent{
            countries = contientCountries.getCountires(index: row) // gets countries for continent
            locationPicker.reloadComponent(countryComponenent) // reload countires
            locationPicker.selectRow(0, inComponent: countryComponenent, animated: true)// set country back to 0
        }
        let continentrow = pickerView.selectedRow(inComponent: continentComponent) //gets selected row for continent
        let countryrow = pickerView.selectedRow(inComponent: countryComponenent)// gets selected row for country
        //put generated text here
        var suggestedLocation = "";
        switch(continentrow){
        case (0):
            if(countryrow == 0) {suggestedLocation = "You should visit New York City"}
            else if(countryrow == 1) {suggestedLocation = "You should visit Cancun"}
            else {suggestedLocation = "You should visit Toronto"}
        case(1):
            if(countryrow == 0) {suggestedLocation = "You should visit Macchu Piccu"}
            else if(countryrow == 1) {suggestedLocation = "You should visit Perito Moreno Glacier"}
            else {suggestedLocation = "You should visit Santiago"}
        case(2):
            if(countryrow == 0) {suggestedLocation = "You should visit Hamburg"}
            else if(countryrow == 1) {suggestedLocation = "You should visit Madrid"}
            else {suggestedLocation = "You should visit Mount Blanc"}
        case(3):
            if(countryrow == 0) {suggestedLocation = "You should visit Hong Kong"}
            else if(countryrow == 1) {suggestedLocation = "You should visit Tokyo"}
            else {suggestedLocation = "You should visit Pai"}
        case(4):
            if(countryrow == 0) {suggestedLocation = "You should visit Cape Town"}
            else if(countryrow == 1) {suggestedLocation = "You should visit Nairobi"}
            else {suggestedLocation = "You should visit Casablanca"}
        default:
            if(countryrow == 0) {suggestedLocation = "You should visit Melbourne"}
            else {suggestedLocation = "You should visit Milford Sound"}
            
        }
        suggestionLabel.text = suggestedLocation
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}

