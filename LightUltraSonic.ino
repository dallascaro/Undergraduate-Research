// HC Sensor with motor control. 

// RGB Sensor with motor control

#include <SparkFunISL29125.h>

#include <Wire.h>

// Create sensor object

SFE_ISL29125 RGB_sensor;

// Motor control will be determined based on the HC sensor readings. 
// 

// Varibales for motor

// Left Motor

int powerA = 3;
int dirA = 12;
int brakeA = 9;

// Right Motor

int powerB = 11;
int dirB = 13;
int brakeB = 8;

// HC SR04 ultrasonic distance sensor
// Distance read is up to 13 feet/ 400 cm

// Lirary for Hc snesor
#include <HCSR04.h>

int trigg = 7;
int echo = 10;

long duration; // travel time from sensor
int distance; // distance of obejcts being read

UltraSonicDistanceSensor distanceSensor(trigg, echo);

void setup()
{

  Serial.begin(9600);

   // Initialize sensor config

  if(RGB_sensor.init())
  {
    Serial.println("Sensor sucess");
  }

  
 // Print values from sensor
  pinMode(trigg, OUTPUT);
  pinMode(echo, INPUT);
  Serial.begin(9600); 

  // Values for Motor
  pinMode(powerA, OUTPUT);
  pinMode(brakeA, OUTPUT);
  pinMode(dirA, OUTPUT);

   pinMode(powerB, OUTPUT);
  pinMode(brakeB, OUTPUT);
  pinMode(dirB, OUTPUT);
  
}
 
 void loop() {

  // Create int values for RGB

   unsigned int red = RGB_sensor.readRed();

  unsigned int green = RGB_sensor.readGreen();

  unsigned int blue = RGB_sensor.readBlue();

  
  digitalWrite(trigg, LOW);
  delayMicroseconds(2); 
  
  digitalWrite(trigg, HIGH);
  delayMicroseconds(10); 
  digitalWrite(trigg, LOW); 
  
  duration = pulseIn(echo, HIGH); // wait for pin to go high and start timiming and wait for it to be low
  // function will return the length based on time it took. 
  
  distance = duration*.034/2;


  if(red > 50 && green < 50 && blue < 50 )
  {
      Serial.print("Red: ");
   Serial.println(red, HEX);
  
   Serial.print("Green: ");
   Serial.println(green, HEX);
  
   Serial.print("Blue: ");
   Serial.println(blue, HEX);

     digitalWrite(brakeA, HIGH);
    digitalWrite(dirA, LOW);
    digitalWrite(powerA, 125);

     digitalWrite(brakeB, HIGH);
    digitalWrite(dirB, LOW);
    digitalWrite(powerB, 125);


    Serial.print("Stopping ");

      delay(3000);
  }

   if(green > 50 && red < 50 && blue < 50)
  {
      Serial.print("Red: ");
   Serial.println(red, HEX);
  
   Serial.print("Green: ");
   Serial.println(green, HEX);
  
   Serial.print("Blue: ");
   Serial.println(blue, HEX);
   
     digitalWrite(brakeA, LOW);
    digitalWrite(dirA, HIGH);
    digitalWrite(powerA, 255);

     digitalWrite(brakeB, LOW);
    digitalWrite(dirB, HIGH);
    digitalWrite(powerB, 255);


    Serial.print("Moving ");

    delay(3000);
  }

   if(blue > 50 && green < 50 && red < 50)
  {
       Serial.print("Red: ");
   Serial.println(red, HEX);
  
   Serial.print("Green: ");
   Serial.println(green, HEX);
  
   Serial.print("Blue: ");
   Serial.println(blue, HEX);

      digitalWrite(brakeA, LOW);
    digitalWrite(dirA, HIGH);
    digitalWrite(powerA, 100);

     digitalWrite(brakeB, LOW);
    digitalWrite(dirB, HIGH);
    digitalWrite(powerB, 100);


    Serial.print("Moving ");

    delay(3000);
  }

  Serial.println();
  delay(1000);
 } 
