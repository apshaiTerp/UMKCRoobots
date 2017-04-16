'''NPL Dates Parser'''
import json
from dateutil import parser
from dateutil.relativedelta import *
import urllib.request
import datetime

# Load JSON from DB
urlAddress = "http://70.94.39.41:8080/email?id=6"
with urllib.request.urlopen(urlAddress) as url:
    data = json.loads(url.read().decode())
    #print(data)

# Detect Time keywords from mesasge
timeKeyword = ["yesterday", "tomorrow", "next week", "next month", "next year", "week", "month", "year"]
keywordList = []
possibleAppointmnt = []
for key, val in data.items():
    if (key == "messageBody"):
        origin_breakdown = data[key].replace(".", "").replace(",", "")  # Strip out comma and periods in the message
        breakdown = origin_breakdown.split()                            # Break down the sentence into individual words

        # Extract time in the message - parser package
        for num in range(len(breakdown)):
            if (breakdown[num] in timeKeyword):
                keywordList.append(breakdown[num])
                time = parser.parse(origin_breakdown, fuzzy=True)

    # Get the time when the email was received & convert millisec to regular time format
    if (key == "dateSent"):
        number = datetime.datetime.fromtimestamp((data[key])/1000.0)
        possibleAppointmnt.append(number)

# Convert time keyword into human readable time format - plus/minus one unit
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

# Convert time keyword into human readable time format - plus/minus more than one unit
multipleValLink = {}
multipleNum = []
for i in range(len(breakdown)):
    if(breakdown[i] == "weeks"):
        multipleNum.append(breakdown[i-1])
        multipleValLink[breakdown[i]] = multipleNum

for key, val in multipleValLink.items():
    if (key.lower() == "weeks"):
        possibleAppointmnt.append((number + relativedelta(weeks=+val)))
    elif(key.lower() == "months"):
        possibleAppointmnt.append((number + relativedelta(months=+val)))
    elif(key.lower() == "years"):
        possibleAppointmnt.append((number + relativedelta(years=+val)))

# Store possible appointments in JSON format with note information
with open('noteJSON.json', 'w') as outputf:
    noteCategory = ["origEmailID", "hostUserID", "date", "time", "meetWithName", "meetWithAddress", "subject", "eventNotes"]
    notedict = {}
    outputList = []

    # Note format
    for count in range(len(possibleAppointmnt)):
        notedict["origEmailID"] = data["emailID"]
        notedict["hostUserID"] = data["recipientID"]
        notedict["date"] = possibleAppointmnt[count].strftime('%m/%d/%Y')
        notedict["time"] = possibleAppointmnt[count].strftime("%H:%M:%S")
        notedict["meetWithAddress"] = data["sender"]
        notedict["subject"] = data["subject"]
        outputList.append(notedict.copy())

    # Put everything into JSON file
    json.dump(outputList, outputf, indent=4)
