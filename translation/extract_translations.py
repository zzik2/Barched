import os
import json

def main():
    base_dir = os.path.dirname(os.path.abspath(__file__))
    keys_file = os.path.join(base_dir, 'keys.txt')
    langs_dir = os.path.join(base_dir, 'langs')
    output_dir = os.path.join(base_dir, 'output')

    os.makedirs(output_dir, exist_ok=True)

    with open(keys_file, 'r', encoding='utf-8') as f:
        keys = [line.strip() for line in f if line.strip()]

    for filename in os.listdir(langs_dir):
        if not filename.endswith('.json'):
            continue

        lang_path = os.path.join(langs_dir, filename)
        
        with open(lang_path, 'r', encoding='utf-8') as f:
            lang_data = json.load(f)

        result = {}
        for key in keys:
            if key in lang_data:
                result[key] = lang_data[key]

        output_path = os.path.join(output_dir, filename)
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(result, f, ensure_ascii=False, indent=2)

    print(f'\ndone: {len(os.listdir(langs_dir))}')

if __name__ == '__main__':
    main()
