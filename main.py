import sys
import requests
from bs4 import BeautifulSoup

URL = sys.argv[1]
page = requests.get(URL)
print(page)

soup = BeautifulSoup(page.content, 'html.parser')

job_elements = soup.find_all('p')

string_ = ""

for i in range(len(job_elements)):
    text = soup.find_all('p')[i].get_text()
    string_ += text

with open('parser_result.txt', 'w', encoding='utf-8') as f:
    f.write(string_)

print("Ready!")