import { Preferences } from '@capacitor/preferences';
import { ItemProps } from '../todo/ItemProps';

const STORAGE_KEY = 'unsynced_items';

export async function saveUnsentItem(item: ItemProps) {
  const existing = await getUnsentItems();
  existing.push(item);
  await Preferences.set({ key: STORAGE_KEY, value: JSON.stringify(existing) });
  console.log('Saved unsynced item locally:', item);
}

export async function getUnsentItems(): Promise<ItemProps[]> {
  const { value } = await Preferences.get({ key: STORAGE_KEY });
  return value ? JSON.parse(value) : [];
}

export async function clearUnsentItems() {
  await Preferences.remove({ key: STORAGE_KEY });
  console.log('Cleared all unsynced items');
}

export async function removeSyncedItem(id: string) {
  const items = await getUnsentItems();
const filtered = items.filter(it => it._id !== id);
  await Preferences.set({ key: STORAGE_KEY, value: JSON.stringify(filtered) });
  console.log('Removed synced item', id);
}
