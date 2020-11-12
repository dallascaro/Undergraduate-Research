
#include <LiquidCrystal.h> // library 

// initialize connected pins

//int lcd;

//LiquidCrystal lcd(8, 9, 10, 11, 12, 13); // put pins your using here

const int rs = 13, en = 12, d4 = 11, d5 = 10, d6 = 9, d7 = 8;

LiquidCrystal lcd (rs, en, d4, d5, d6, d7);


void setup() {
  // put your setup code here, to run once:
  lcd.begin(16,2);

  Serial.begin(9600); // initialize serial comm

  Serial.setTimeout(50); // setting time to wait for input

}



void loop() {
  // put your main code here, to run repeatedly:

  String text = Serial.readString(); // read text in String

  String line1 = text.substring(0, 16); // amount of characters for lcd clock

  String line2 = text.substring(16, 32);

  if (text.length() > 0) {
    lcd.setCursor (0, 0);
    lcd.print ("                     ");

    lcd.setCursor (0, 1);
    lcd.print ("                  ");
  }

    lcd.setCursor(0, 0);
    lcd.print(line1);
    lcd.setCursor(0, 1);
    lcd.print(line2);
}
