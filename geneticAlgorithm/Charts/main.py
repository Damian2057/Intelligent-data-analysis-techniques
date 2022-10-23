import numpy as np
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib as mpl

df = pd.read_csv('data.csv')

data = df.groupby(["epoch"], as_index=False).agg(
    mean=pd.NamedAgg(column="adaptation", aggfunc=np.mean))
data.reset_index(inplace=True)


ax = data.plot(x='epoch', y='mean', c='brown')
# ax.fill_between(x='epoch', y1='min', y2='max', data=data,
#                 color=mpl.colors.to_rgba('brown', 0.15))
plt.show()