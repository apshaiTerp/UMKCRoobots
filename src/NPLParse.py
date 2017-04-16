'''NPL Dates Parser'''
import json
from dateutil import parser
from dateutil.relativedelta import *
import urllib.request
import datetime


# Load JSON from DB
urlAddress = "http://70.94.39.41:8080/email?id=4"
with urllib.request.urlopen(urlAddress) as url:
    data = json.loads(url.read().decode())
    #print(data)

# Words that we wish to detect
timeKeyword = ["yesterday", "tomorrow", "next week", "next month", "next year", "week", "month", "year"]
keywordList = []
for key, val in data.items():
    if (key == "messageBody"):
        origin_breakdown = data[key].replace(".", "").replace(",", "")
        breakdown = origin_breakdown.split()
        print(breakdown)

        # Extract time in the message
        for num in range(len(breakdown)):
            if (breakdown[num] in timeKeyword):
                keywordList.append(breakdown[num])
                #print(dateparser.parse(breakdown[num]))
                time = parser.parse(origin_breakdown, fuzzy=True)
                print("Time from msg:")
                print(time, "\n")

    # Get the time when the email was received & convert millisec to regular time format
    if (key == "dateSent"):
        #print(data[key])
        number = datetime.datetime.fromtimestamp((data[key])/1000.0)
        print("Converted Date:")
        print(number, "\n")

# Generate times for appointment
possibleAppointmnt = []
for timeWord in keywordList:
    if (timeWord.lower() == "tomorrow"):
        possibleAppointmnt.append((number + relativedelta(days=+1)))
    elif(timeWord.lower() == "yesterday"):
        possibleAppointmnt.append((number + relativedelta(days=-1)))
    elif (timeWord.lower() == "next month"):
        possibleAppointmnt.append((number + relativedelta(months=+1)))
    elif (timeWord.lower() == "next week"):
        possibleAppointmnt.append((number + relativedelta(weeks=+1)))
    elif(timeWord.lower() == "next year"):
        possibleAppointmnt.append((number + relativedelta(years=+1)))

print(possibleAppointmnt[0])







teststring = "I would like to meet with you after 2 weeks. If that does not work for you, I can stop by 4/17/2016 at 5pm. " \
             "I can also talk with you over the phone this afternoon."
test = teststring.split()
print(test) #breakdown
print(test[test.index("weeks.")-1])  # get the index of the weeks for the number
