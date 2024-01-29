import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import FuncFormatter
import os
import time
import sys

df = pd.read_csv(r"C:\Users\kmcel\Documents\Capstone_two\CensusController\chartResult.csv")

timestamp = sys.argv[1]

df.rename(columns={
    'number_of_establishments': 'establishments',
    'employment': 'employment',
    'annual_payroll_in_thousands': 'annual payroll'
}, inplace=True)

plt.figure(figsize=(10, 6))

colors = {'establishments': 'blue', 'employment': 'green', 'annual payroll': 'orange'}

def millions_formatter(x, pos):
    return f'{x / 1000000}'

population = df.columns[1]
color = colors.get(population)

fig, ax = plt.subplots()
ax.plot(df['year'], df[population], label=population, color=color, marker='o')
plt.xticks(range(min(df['year']), max(df['year']+1)))
ax.yaxis.set_major_formatter(FuncFormatter(millions_formatter))

plt.xlabel('Year')
plt.ylabel('Value (in millions)')
plt.title(f'Census National Averages for {population}')
plt.legend()


image_file_path = os.path.join(r"C:\Users\kmcel\Documents\Capstone_two\CensusController\src\main\resources\static", f'image_{timestamp}.jpg')

plt.savefig(image_file_path)

plt.close()


