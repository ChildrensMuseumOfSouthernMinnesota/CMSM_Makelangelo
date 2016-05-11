import email, imaplib, os, subprocess, smtplib, time

detach_dir = os.getcwd()+"\\DrawingFiles\\"

configFile = open("userConfig.txt", 'r')

user = configFile.readline().strip()
pwd = configFile.readline().strip()

#smtp = smtplib.SMTP_SSL("smtp.gmail.com", 465)
#smtp.login(user, pwd)
m = imaplib.IMAP4_SSL('imap.gmail.com')
m.login(user,pwd)
m.select('Inbox')
resp, items = m.search(None, "(BODY 'Draw')", "(UNSEEN)")
items = items[0].split()

for emailid in items: # Iterates through email messages
	resp, data = m.fetch(emailid, "(RFC822)")
	email_body = data[0][1]
	mail = email.message_from_string(email_body)
	origSender = mail["From"]
	if mail.get_content_maintype() != 'multipart': # If the message doesn't have multiple parts, it can't have an attachment
		continue
	
	for part in mail.walk(): # Step through each part of the message
		
		if part.get_content_maintype() == 'multipart': # if it's still a multipart, ignore it
			continue
		
		if part.get('Content-Disposition') is None:
			continue
	
		filename = part.get_filename()

		att_path = os.path.join(detach_dir, filename)

		if not os.path.isfile(att_path):
			fp = open(att_path, 'wb')
			fp.write(part.get_payload(decode=True))
			fp.close()
			#smtp.sendmail("founders401printing@gmail.com", origSender, """From: Founders 401 Printing <founders401printing@gmail.com
			#							      To: You <"""+origSender+""">
			#							      Subject: File Received
			#							      Your file has been received and will be printed shortly""")
			#printFile(filename, copies, doubleOption)

	m.store(emailid, '+FLAGS', '\Seen')
m.logout()

files = os.listdir(detach_dir)
curr_time = time.time()

for f in files:
        timeDelta = curr_time-os.stat(os.path.join(detach_dir, f)).st_ctime
        weekDelta = timeDelta/60/60/24/7
        if (weekDelta > 1):
                os.remove(os.path.join(detach_dir, f))
        print weekDelta

